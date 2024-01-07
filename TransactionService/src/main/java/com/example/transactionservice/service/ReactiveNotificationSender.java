package com.example.transactionservice.service;

import com.example.transactionservice.model.dto.PayoutPayload;
import com.example.transactionservice.model.dto.TransactionPayload;
import com.example.transactionservice.model.entity.Payout;
import com.example.transactionservice.repository.PayoutRepository;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReactiveNotificationSender {

    private final WebClient webClient = WebClient.builder().build();

    private final TransactionRepository transactionRepository;
    private final PayoutRepository payoutRepository;

    @Scheduled(cron = "0 */5 * * * *")  // Расписание каждые 5 минут
    @SchedulerLock(name = "sendNotifications", lockAtLeastFor = "PT4M", lockAtMostFor = "PT5M")
    public void sendNotifications() {
        LockAssert.assertLocked();

        transactionRepository.findByStatus("PENDING")
                .flatMap(transaction -> sendNotification(transaction.getNotificationUrl(), transaction))
                .subscribe();

        payoutRepository.findByStatus("PENDING")
                .flatMap(payout -> sendNotification(payout.getNotificationUrl(), payout))
                .subscribe();
    }


    public Mono<Void> sendNotification(String notificationUrl, Object payload) {
        return webClient.post()
                .uri(notificationUrl)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
