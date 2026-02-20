class Bank {
  #balance

  constructor() {
    this.#balance = 0
  }

  #updateBalance(updatedBalance) {
    this.#balance = updatedBalance
  }

  addBalance(amount) {
    this.#updateBalance(amount + this.#balance)
  }
}

const b = new Bank()
