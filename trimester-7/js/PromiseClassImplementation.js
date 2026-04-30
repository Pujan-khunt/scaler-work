class Promise {
  constructor(executor) {
    this.state = "pending";
    this.value = undefined;
    this.reason = undefined;

    const resolve = (value) => {
      if (this.state === "pending") {
        this.state = "fulfilled"
        this.value = value;
      }

      this.onFulfilledCallbacks.forEach(fn => fn());
    }

    const reject = (reason) => {
      if (this.state === "pending") {
        this.state = "rejected"
        this.reason = reason;
      }

      this.onRejectedCallbacks.forEach(fn => fn());
    }

    try {
      executor(resolve, reject);
    } catch (err) {
      reject(err);
    }
  }

  then(onFullfilled, onRejected) {
    if (typeof onFullfilled !== "Function") {
      onFullfilled = (value) => value;
    }

    if (typeof onRejected !== "Function") {
      onRejected = (reason) => { throw reason };
    }

    if (this.state === "fulfilled") {
      onFullfilled(this.value);
    }
    else if (this.state === "rejected") {
      onRejected(this.reason);
    }
    else {
      this.onFulfilledCallbacks.push(onFullfilled);
      this.onRejectedCallbacks.push(onRejected);
    }
  }

  catch(onRejected) {
    if (typeof onRejected !== "Function") {
      onRejected = (reason) => { throw reason };
    }

    if (this.state === "rejected") {
      onRejected(this.error)
    } else {
      this.onRejectedCallbacks.push(onRejected);
    }
  }

  finally(onFinally) {
    if (typeof onFinally === "Function") {
      onFinally();
    }
  }
}
