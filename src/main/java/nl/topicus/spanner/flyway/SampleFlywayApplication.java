/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.topicus.spanner.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import nl.topicus.spanner.flyway.entities.CustomerRepository;

@SpringBootApplication
public class SampleFlywayApplication
{

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(SampleFlywayApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public FlywayMigrationStrategy cleanMigrateStrategy()
	{
		FlywayMigrationStrategy strategy = new FlywayMigrationStrategy()
		{
			@Override
			public void migrate(Flyway flyway)
			{
				flyway.clean();
				flyway.migrate();
			}
		};
		return strategy;
	}

	@Bean
	public CommandLineRunner runner(CustomerRepository repository)
	{
		return new CommandLineRunner()
		{

			@Override
			public void run(String... args) throws Exception
			{
				System.err.println(repository.findAll());
			}

		};
	}

}
