public class Customer {
    private String name;
    private String taxNumber;

    public Customer(String name, String taxNumber) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Customer name must not be null");
        }

        if (taxNumber == null || taxNumber.isEmpty()) {
            throw new NullPointerException("Customer tax number must not be null");
        }

        this.name = name;
        this.taxNumber = taxNumber;
    }

    public String getName() {
        return name;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                '}';
    }
}
