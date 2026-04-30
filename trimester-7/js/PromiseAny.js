Promise.myAny = function(arr) {
  return new Promise((resolve, reject) => {
    const failedResults = [];
    let rejected = 0;

    for (let i = 0; i < arr.length; i++) {
      Promise.resolve(arr[i]).then((value) => {
        resolve(value);
      }).catch((error) => {
        failedResults[i] = error;
        rejected++;

        if (rejected == arr.length) {
          reject(new AggregateError(failedResults, "All promise were rejected"));
        }
      });
    }
  });
}
