Array.prototype.myFilter = function(callback, thisArg) {
  const arr = this;
  const result = [];

  for (let i = 0; i < arr.length; i++) {
    if (i in arr) {
      if (callback.call(thisArg, arr[i], i, arr)) {
        result.push(arr[i]);
      }
    }
  }

  return result;
};
