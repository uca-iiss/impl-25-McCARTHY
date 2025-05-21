package payment

class CreditCard(cardNumber: String, var CreditLimit: Money) extends PaymentMethod {

    val name: String = "Tarjeta de Crédito"

    def pay(amount: Money): TransactionStatus = {
        if(amount <= CreditLimit) {
            CreditLimit -= amount
            logTransaction(amount, Success)
            Success
        } else {
            logTransaction(amount, Failure)
         
        }
    }
}