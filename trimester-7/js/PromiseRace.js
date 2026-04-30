Promise.myRace = function(arr) {
  return new Promise((resolve, reject) => {
    if (arr.length === 0) {
      // stays pending forever
      return
    }
    for (let i = 0; i < arr.length; i++) {
      Promise.resolve(arr[i]).then(resolve).catch(reject)
    }
  });
}
