package db.migration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.api.migration.spring.BaseSpringJdbcMigration;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToBytesTransformer;
import org.springframework.jdbc.core.JdbcTemplate;

import nl.topicus.spanner.flyway.entities.Customer;
import nl.topicus.spanner.flyway.entities.Invoice;

public class V1_1__InitData extends BaseSpringJdbcMigration
{
	private static final String INSERT_CUSTOMER = "INSERT INTO customer (id, uuid, created, updated, firstName, lastName) VALUES (?,?,?,?,?,?)";

	private static final String INSERT_INVOICE = "INSERT INTO invoice (id, uuid, created, updated, customer, invoiceNumber, description, amount) VALUES (?,?,?,?,?,?,?,?)";

	@Override
	public void migrate(JdbcTemplate jdbcTemplate)
	{
		List<Customer> customers = createCustomers(jdbcTemplate);
		createInvoices(jdbcTemplate, customers);
	}

	private List<Customer> createCustomers(JdbcTemplate jdbcTemplate)
	{
		List<Customer> res = new ArrayList<>();
		String[][] customers = new String[][] { { "John", "Doe" }, { "Mary", "Johnson" } };
		for (String[] cust : customers)
		{
			Customer customer = new Customer(cust[0], cust[1]);
			customer.initId();
			jdbcTemplate.update(INSERT_CUSTOMER, customer.getId(),
					ToBytesTransformer.INSTANCE.transform(customer.getUuid()), customer.getCreated(),
					customer.getUpdated(), customer.getFirstName(), customer.getLastName());
			res.add(customer);
		}
		return res;
	}

	private void createInvoices(JdbcTemplate jdbcTemplate, List<Customer> customers)
	{
		Object[][] invoices = new Object[][] { { "123", BigDecimal.valueOf(149.90), "Test description" },
				{ "124", BigDecimal.valueOf(250), "Test description" } };
		int index = 0;
		for (Object[] inv : invoices)
		{
			Invoice invoice = new Invoice(customers.get(index), (String) inv[0], (BigDecimal) inv[1], null);
			invoice.setDescription((String) inv[2]);
			invoice.initId();
			jdbcTemplate.update(INSERT_INVOICE, invoice.getId(),
					ToBytesTransformer.INSTANCE.transform(invoice.getUuid()), invoice.getCreated(),
					invoice.getUpdated(), invoice.getCustomer().getId(), invoice.getInvoiceNumber(),
					invoice.getDescription(), invoice.getAmount());
			index++;
		}
	}

}
