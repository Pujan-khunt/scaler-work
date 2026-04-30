Promise.myAll = function(arr) {
  return new Promise((resolve, reject) => {
    const results = [];
    let completed = 0;
    let total = 0;

    for (let i = 0; i < arr.length; i++) {
      total++;

      Promise.resolve(arr[i])
        .then((value) => {
          // ensures values are in order despite different resolution time.
          results[i] = value;
          completed++;

          if (completed == total) {
            resolve(results);
          }
        })
        .catch((error) => {
          // fail fast
          reject(error);
        })
    }
  });
}
