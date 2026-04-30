function throttle(fn, delay) {
  let lastCallTime = 0;

  return function(...args) {
    const now = Date.now();

    if (now - lastCallTime >= delay) {
      lastCallTime = now;
      fn.apply(this, args);
    }
  }
}
