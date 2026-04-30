Promise.myAllSettled = function(arr) {
  return new Promise((resolve) => {
    const results = [];
    let completed = 0;

    for (let i = 0; i < arr.length; i++) {
      total++;

      Promise.resolve(arr[i]).then((value) => {
        results[i] = { status: "fulfilled", value: value };
      }).catch((error) => {
        results[i] = { status: "rejected", reason: error };
      }).finally(() => {
        completed++;
        if (completed === arr.length) {
          resolve(results);
        }
      })
    }
  });
}
