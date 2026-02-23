
// Create new loan
API    : http://localhost:8081/loan-payment-system/api/v1/loan
METHOD : POST 
Payload: {"initialAmount":"20000.00", "term":"24"}

// Pay loan
API    : http://localhost:8081/loan-payment-system/api/v1/payment
METHOD : POST 
Payload: {"loanId":"1","paymentAmount":"2500.00"}

// Check balance and terms
GET: http://localhost:8081/loan-payment-system/api/v1/loans/1
