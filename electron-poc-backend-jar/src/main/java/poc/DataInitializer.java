package poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import poc.dto.Order;
import poc.repo.OrderRepository;

import java.math.BigDecimal;
import java.util.Date;

@Component
class DataInitializer implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        if (orderRepository.findAll().isEmpty()) {
            loadInitialData();
        }
    }

    private void loadInitialData() {
        Order order1 = new Order();
        order1.setCustomerName("John");
        order1.setAmount(200);
        order1.setItemName("ZylinderSchrauben DIN 6912 Edelstahl A2 (M4x18)");
        order1.setPricePerUnit(BigDecimal.valueOf(0.49));
        order1.setCreationDate(new Date(System.currentTimeMillis() - 8640000));
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setCustomerName("Anna");
        order2.setAmount(4);
        order2.setItemName("Barum Braburis Reifen 205/55 R16 91V");
        order2.setPricePerUnit(BigDecimal.valueOf(63.72));
        order2.setCreationDate(new Date(System.currentTimeMillis() - 17640000));
        orderRepository.save(order2);
    }
}