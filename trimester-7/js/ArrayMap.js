Array.prototype.myMap = function(callback, thisArg) {
  const arr = this;
  const result = new Array(arr.length);

  for (let i = 0; i < arr.length; i++) {
    if (i in arr) {
      result[i] = callback.call(thisArg, arr[i], i, arr);
    }
  }
}
