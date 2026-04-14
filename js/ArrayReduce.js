Array.prototype.myReduce = function(callback, initialValue) {
  const arr = this;
  let accumulator = initialValue;
  let startIndex = 0;

  if (initialValue === undefined) {
    // find first defined element (important for sparse arrays)
    let i = 0;
    while (i < arr.length && !(i in arr)) {
      i++;
    }

    if (i === arr.length) {
      throw new TypeError("Reduce of empty array with no initial value");
    }

    accumulator = arr[i];
    startIndex = i + 1;
  }

  for (let i = startIndex; i < arr.length; i++) {
    if (i in arr) {
      accumulator = callback(accumulator, arr[i], i, arr);
    }
  }

  return accumulator;
};
