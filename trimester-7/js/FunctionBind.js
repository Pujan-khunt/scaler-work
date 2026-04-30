Function.prototype.myBind = function(context, ...bindArgs) {
  const fn = this;
  return function(...lateArgs) {
    return fn.apply(context, [...bindArgs, ...lateArgs]);
  }
}
