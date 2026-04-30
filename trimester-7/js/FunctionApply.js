Function.prototype.myApply = function(context, args) {
  context ??= globalThis
  const s = Symbol();
  context[s] = this;
  const r = context[s](...args)
  delete context[s]
  return r;
};
