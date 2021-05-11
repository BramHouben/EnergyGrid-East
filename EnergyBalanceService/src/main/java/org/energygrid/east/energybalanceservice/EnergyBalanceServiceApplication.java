package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@EnableMongoRepositories(basePackageClasses = EnergyBalanceRepo.class)
@EnableScheduling
@SpringBootApplication
public class EnergyBalanceServiceApplication {

    @Autowired
    private EnergyBalanceRepo repo;

    @Autowired
    private EnergyBalanceStoreRepo storeRepo;

    public static void main(String[] args) {
        SpringApplication.run(EnergyBalanceServiceApplication.class, args);
    }

    @Bean
    void test() throws InterruptedException {
        repo.deleteAll();
        storeRepo.deleteAll();
        var energyBalance = new EnergyBalance(UUID.randomUUID(), 100000000000L, 102000000000L, 102, LocalDateTime.now(ZoneOffset.UTC));

        TimeUnit.SECONDS.sleep(1);
        var energyBalance2 = new EnergyBalance(UUID.randomUUID(), 100000000000L, 98000000000L, 98, LocalDateTime.now(ZoneOffset.UTC));
//        TimeUnit.SECONDS.sleep(1);
//        var energyBalance3 = new EnergyBalance(UUID.randomUUID(), 100000000000L, 100000000000L, 100, LocalDateTime.now(ZoneOffset.UTC));

        repo.insert(energyBalance);
        repo.insert(energyBalance2);
//        repo.insert(energyBalance3);
    }


}
