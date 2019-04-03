const raw = require('./raw1.json');
const constant = require('./constant.json');

const ONE_DAY = 1000 * 3600 * 24;
const ONE_MONTH = ONE_DAY * 30;
const ONE_YEAR = ONE_DAY * 365;

const START_DATE = new Date(2017, 0, 1);
const END_DATE = new Date(2017, 6, 5, 23, 59);
const WORK_YEARS = END_DATE.getFullYear() - START_DATE.getFullYear();
const WORK_MONTHS = 3 //WORK_YEARS * 12;

const allRawProd = raw.packages.reduce((rs, p) => rs.concat(p.products), []).map((p, i) => {
  p.product_id = i;
  return p;
});

//config
const nPkg = 6;
const nProdPerPkgMin = 5;
const nProdPerPkgMax = 10;
const nContractor = 5;


function main() {
  log({
    project_id: 0,
    inflation_rate: 0.01,
    start_date: toDate(START_DATE),
    packages: randPackages(nPkg, nProdPerPkgMin, nProdPerPkgMax),
    product: allRawProd.map(p =>  ({
      product_id: p.product_id,
      description: p.description,
      unit: p.unit,
    })),
    contractors: randContractors()
  });
}

function randPackages(nPkg, nProdPerPkgMin, nProdPerPkgMax) {
  const rs = [];
  for (let i = 0; i < nPkg; i++) {
    const pkg = {};
    const nProd = randn(nProdPerPkgMin, nProdPerPkgMax);
    pkg.package_id = i;
    pkg.timeline = randTimeline();
    pkg.joined_contractors = randJoinedContractors();
    pkg.products = shuffle(range(0, allRawProd.length - 1)).slice(0, nProd).map(pId => ({
      product_id: pId,
      quantity: randn(1, allRawProd[pId].quantity)
    }));
    pkg.estimated_cost = Math.round(pkg.products.reduce((rs, p) => rs + p.quantity * allRawProd[p.product_id].price, 0) / 100000) * 80;
    rs.push(pkg);
  }
  return rs;
}

function randContractors() {
  const names = constant.contractor_names.slice(0, nContractor);
  const rs = [];
  for (let i = 0; i < nContractor; i++) {
    const contractor = {};
    contractor.contractor_id = i;
    contractor.description = names[i];
    contractor.quality = toFixed(Math.random());
    contractor.relationship = toFixed(Math.random());
    contractor.products = [];
    for (let j = 0; j < allRawProd.length; j++) {
      const rawProd = allRawProd[j];
      const exPrice = randPrice(rawProd.price, contractor.quality);
      contractor.products.push({
        product_id: rawProd.product_id,
        sell_price: exPrice.sell_price,
        buy_price: exPrice.buy_price,
        discounts: randDiscounts(randn(2, 6), exPrice)
      });
    }

    rs.push(contractor);
  }
  return rs;
}


function randPrice(mainPrice, factor, delta=0.5) {
  const up = mainPrice * (1 + delta);
  const down = mainPrice * (1 - delta);
  const p1 = Math.round(expand(factor * Math.random(), down, up) / 1000) ;
  const p2 = Math.round(expand(factor * Math.random(), down, up) / 1000) ;
  if (p1 > p2) {
    return {
      sell_price: p1,
      buy_price: p2
    }
  }
  return {
    sell_price: p2,
    buy_price: p1
  }
}

function randDiscounts(n, exPrice, start=START_DATE, end=END_DATE) {
  const startTime = start.valueOf();
  const endTime = end.valueOf();
  const bins = 50;
  const ranges = randSplit(n, bins);
  const rs = [];
  for (let i = 1; i < ranges.length; i++) {
    const from = expand(shrink(ranges[i - 1], 0, bins), startTime, endTime);
    const to = expand(shrink(ranges[i], 0, bins), startTime, endTime);
    rs.push({
      from: toDate(new Date(from)),
      to: toDate(new Date(to)),
      rate: randRate()
    })
  }

  return rs;

  function randRate() {
    let rate = toFixed(Math.random());
    const rule = `
      (1 - rate) * exPrice.sell_price - exPrice.buy_price > 0
    `;
    while(!eval(rule)) {
      rate = toFixed(Math.random());
    }
    return rate;
  }
}

function randJoinedContractors() {
  const nJoinedContractor = randn(2, nContractor);
  const contractors = shuffle(range(0, nContractor - 1));
  return contractors.slice(0, nJoinedContractor);
}

function randTimeline() {
  const rule = `
    timeline.to - timeline.from > ONE_MONTH * WORK_MONTHS * 0.1
    && timeline.to - timeline.from < ONE_MONTH * WORK_MONTHS * 0.9
  `;
  const timeline = {
    from: 0,
    to: 0
  };
  while(!eval(rule)) {
    timeline.from = randDate();
    timeline.to = randDate();
    if (timeline.from > timeline.to) {
      const temp = timeline.from;
      timeline.from = timeline.to;
      timeline.to = temp;
    }
  }

  return {
    from: toDate(timeline.from),
    to: toDate(timeline.to)
  };
}

function randDate() {
  const from = START_DATE.valueOf();
  const to = END_DATE.valueOf();
  return new Date(randn(from, to));
}

function randn(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function sample(arr) {
  const i = randn(0, arr.length - 1);
  return arr[i];
}

function shuffle(arr) {
  const temp = arr.map(value => ({
    key: Math.random(),
    value
  }));

  temp.sort((e1, e2) => e1.key - e2.key);
  return temp.map(e => e.value);
}

function samples(n) {
  const arr = range(0, n - 1);
  return shuffle(arr);
}

function range(start, end) {
  const rs = [];
  for (let i = start; i <= end; i++) {
    rs.push(i);
  }

  return rs;
}

function randSplit(n, bins) {
  const set = new Set();
  for (let i = 0; i < n - 1; i++) {
    set.add(randn(1, bins - 1));
  }
  set.add(0);
  set.add(bins);

  const ranges = [];
  set.forEach(v => {
    ranges.push(v);
  });

  sort(ranges);

  return ranges;
}

function log(obj, nice=true) {
  console.log(JSON.stringify(obj, null, nice ? 2 : null));
}

function toFixed(number) {
  return Math.round(number * 100) / 100;
}

function expand(factor, start, end) {
  return start + factor * (end - start);
}

function shrink(value, start, end) {
  return (value - start) / (end - start);
}

function toDate(d) {
  return `${d.getDate()}/${d.getMonth()+1}/${d.getFullYear()}`
}

function sort(arr) {
  arr.sort((a, b) => a - b);
}


main();
