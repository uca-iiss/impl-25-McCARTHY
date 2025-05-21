package payment

type Money = Double 

//Estado de la transacción
selead trait TransactionStatus

case object Success extends TransactionStatus
case object Failure extends TransactionStatus