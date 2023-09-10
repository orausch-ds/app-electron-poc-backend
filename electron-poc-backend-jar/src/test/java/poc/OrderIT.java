package poc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import poc.dto.Order;
import poc.repo.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void get_orders() throws Exception {

        // ARRANGE
        final Order order1 = new Order();
        order1.setId(3L);
        final Order order2 = new Order();
        order2.setId(4L);
        final Order order3 = new Order();
        order3.setId(5L);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        // ACT
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andReturn();

        // ASSERT
        final String jsonContent = mvcResult.getResponse().getContentAsString();
        final List<Order> orders = objectMapper.readValue(jsonContent, new TypeReference<>() {
        });

        assertThat(orders).hasSize(5)
                .satisfies(orderList -> assertThat(orderList.stream().map(Order::getId).toList()).contains(3L, 4L, 5L));
    }

    @Test
    public void create_order() throws Exception {

        // ARRANGE
        final Order order = new Order();
        order.setAmount(500);
        order.setCustomerName("Tester");
        order.setPricePerUnit(BigDecimal.valueOf(19.99));

        // ACT
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/order")
                .content(objectMapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

        // ASSERT
        final List<Order> orderList = orderRepository.findAll();

        order.setId(3L);
        assertThat(orderList).hasSize(3);

        final Order createdOrder = orderList.get(2);
        assertThat(createdOrder.getId()).isEqualTo(3L);
        assertThat(createdOrder.getCustomerName()).isEqualTo("Tester");
    }

    @Test
    public void delete_orders() throws Exception {

        // ARRANGE
        final List<Order> orders = orderRepository.findAll();

        // ACT
        for (Order order : orders) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/orders/order/" + order.getId()))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
        }

        // ASSERT
        assertThat(orderRepository.findAll()).isEmpty();
    }
}
