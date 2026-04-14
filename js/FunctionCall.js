Function.prototype.myCall = function(context, ...args) {
  context = context ?? globalThis
  const symbol = Symbol()
  context[symbol] = this
  const result = context[symbol](...args)
  delete context[symbol]
  return result;
}
