package procurement;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import procurement.entity.procurement.*;
import procurement.entity.procurement.Package;
import org.moeaframework.core.spi.ProviderLookupException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProcurementProblem implements Problem {

    // number of providers
    private int numProvider;

    // number of round
    private int numRound;

    private float inflationRate;
    private Date startDate;

    private ArrayList<Package> packages;
    private ArrayList<Provider> providers;
    private Boolean debug;
    private static int i = 0;//test

    // load data input from file reader
    public ProcurementProblem(String filename) throws IOException {
        super();
        load(filename);
        debug = false;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    private Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date result;
        try {
            result = dateFormat.parse(date);
        } catch (ParseException e) {
            System.err.println("Date parsing exception!");
            result = new Date();
        }
        return result;
    }

    private void load(String filename) throws IOException {

        // read first file for buyer information
        System.out.println("Reading... " + filename);

        ObjectMapper objectMapper = new ObjectMapper();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        objectMapper.setDateFormat(df);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        ProcurementData data = objectMapper.readValue(new File(filename), ProcurementData.class);

        numRound = data.getPackages().size();
        numProvider = data.getProviders().size();
        System.out.println(String.format("Got %d round(s) procurement, %d provider(s)", numRound, numProvider));

        packages = data.getPackages();
        providers = data.getProviders();
        inflationRate = data.getInflationRate();
        startDate = data.getStartDate();
    }

    public int getNumProvider() {
        return numProvider;
    }

    public void setNumProvider(int numProvider) {
        this.numProvider = numProvider;
    }

    public int getNumRound() {
        return numRound;
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }

    @Override
    public String getName() {
        return "Procurement Problem";
    }

    //ask
    @Override
    public int getNumberOfVariables() {
        return numRound * 2;
    }

    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public int getNumberOfConstraints() {
		/* Algorithm use constrain */
    	return 1;
    	
    	/* Algorithm dont use constrain */
//        return 0;
    }

    @Override
    public void close() {

    }

    private int dayRange(Discount timeline) {
        int diffDays = (int) ((timeline.getTo().getTime() - timeline.getFrom().getTime()) / (1000 * 60 * 60 * 24));
        return diffDays;
    }

    private int dayRange(Date from, Date to) {
        int diffDays = (int) (to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24);
        return diffDays;
    }

    public Date addDay(Date date, int numDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, numDay); //minus number would decrement the days
        return cal.getTime();
    }

    @Override
    public void evaluate(Solution solution) {
        float buyerProfit = 0;

        float[] providerProfit = new float[numProvider];
        float[] providerPriceAtRound = new float[numRound];
        float[] inflationRateAtRound = new float[numRound];
        float[] relation = new float[numProvider];

        Date[] dateChoice = new Date[numRound];

        // decision variables
        int[] providerChoice = EncodingUtils.getInt(solution);

        // convert number of day to execution date && inflation rate
        for (int roundIndex = 0; roundIndex < numRound; roundIndex++) {
           dateChoice[roundIndex] = addDay(packages.get(roundIndex).getTimeline().getFrom(), providerChoice[numRound + roundIndex]);
           inflationRateAtRound[roundIndex] = (float) Math.exp(inflationRate * dayRange(startDate, dateChoice[roundIndex]));
       }

        // calculate each provider's profit
        for (int providerIndex = 0; providerIndex < numProvider; providerIndex++) {
            providerProfit[providerIndex] = 0;
            relation[providerIndex] = providers.get(providerIndex).getRelationship();
        }

        float totalTrust = 0;

        // calculate provider price at execution date + provider profit
        for (int roundIndex = 0; roundIndex < numRound; roundIndex++) {

            // get provider id from decision variable
            int providerIndex = packages.get(roundIndex).getJoinedProviders().get(providerChoice[roundIndex]);
            Provider selectedProvider = providers.get(providerIndex);

            // calculate price for this provider id
            float totalPrice = 0;

            for (ProductNeed p : packages.get(roundIndex).getProducts()) {
                // get sell price for this package
                totalPrice += p.getQuantity() * selectedProvider.getProductById(p.getId()).getPriceAtDate(dateChoice[roundIndex]);

                // provider profit
                providerProfit[providerIndex] += p.getQuantity() * (selectedProvider.getProductById(p.getId()).getSellPrice() - providers.get(providerIndex).getProductById(p.getId()).getBuyPrice());

                // trust value of provider
                totalTrust += selectedProvider.getQuality();
            }
			providerPriceAtRound[roundIndex] = totalPrice;
//			System.out.println(i++); //test
			
            if (debug) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println(String.format("        Package %d :  Provider %d", roundIndex, providerIndex, providers.get(providerIndex).getDescription()));
                System.out.println(String.format("                   Start date  %s", df.format(dateChoice[roundIndex])));
                System.out.println(String.format("                   Package cost: %,d", (long) totalPrice) );
                //System.out.println(String.format("                   Provider profit: %,d", (long) providerProfit[providerIndex]) );
            }
        }

        // calculate buyer's profit
        for (int roundIndex = 0; roundIndex < numRound; roundIndex++) {
            buyerProfit += packages.get(roundIndex).getEstimatedCost() - providerPriceAtRound[roundIndex] * inflationRateAtRound[roundIndex];
        }

        // sum of all providers profit, with relationship ratio as weights
        float sumOfProviderProfit = 0;
        for (int providerIndex = 0; providerIndex < numProvider; providerIndex++) {
            sumOfProviderProfit += providerProfit[providerIndex] * relation[providerIndex];
        }

        float profitDiff = 0;
        // calculate difference between providers profit
        float numCompare = (numProvider - 1) * numProvider / 2;
        for (int i = 0; i < numProvider - 1; i++)
            for (int j = i + 1; j < numProvider; j++) {
                profitDiff += Math.abs(providerProfit[i] * relation[j] - providerProfit[j] * relation[i]) / numCompare;
            }
/// 1 2 ---

        solution.setObjective(0, -buyerProfit);
        solution.setObjective(1, -sumOfProviderProfit);
        solution.setObjective(2, -totalTrust);
        solution.setObjective(3, profitDiff);

        // constraint if buyer profit is >= 0
        float buyerProfitable = buyerProfit >= 0 ? 0.0f : buyerProfit;
        
		/* Set constrain for solution */ //comment when use ECEA
        solution.setConstraint(0, buyerProfitable);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        
        
        // choice of provider for each procurement round
        for (int i = 0; i < numRound; i++) {
            //contain the ID of joined provider, not the whole provider
            solution.setVariable(i, EncodingUtils.newInt(0, packages.get(i).getJoinedProviders().size() - 1));
        }
        // 1: 3, 2: 4 , 3,4
        // choice of execution date for each round
        for (int i = 0; i < numRound; i++) {
            solution.setVariable(numRound + i, EncodingUtils.newInt(0, dayRange(packages.get(i).getTimeline())));
        }
        return solution;
    }
    
}
