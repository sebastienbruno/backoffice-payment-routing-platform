package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.batch;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.config.JmsConfig;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class MessageScheduler {

  private final JmsTemplate jmsTemplate;
  private final Random random = new Random();

  private final List<String> senders = List.of(
    "CoreBankingSystem", "TradingApp", "LiquidityMonitor", "FraudSystem", "BackOffice"
  );

  private final List<String> recipientsPool = List.of(
    "swift-inbound", "sepa-clearing", "fx-confirmation", "custody-alerts", "instant-payments",
    "treasury-ops", "cb-notifier", "ach-core", "payment-hub", "liquidity-gateway",
    "sanctions-filter", "aml-monitoring", "compliance-alerts", "cb-integration", "trade-matching",
    "reconciliation-engine", "payment-tracker", "partner-onboarding", "fraud-detection",
    "limit-checker", "credit-risk-system", "external-gateway", "client-messaging", "cash-pooling",
    "nostro-monitor", "accounting-feed", "fee-calculation", "routing-orchestrator", "netting-engine",
    "alerting-dashboard"
  );

  public MessageScheduler(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Scheduled(fixedRate = 10_000) // every 100 seconds
  public void sendScheduledMessage() {
    String sender = getRandom(senders);
    String payload = generateRandomPayload();
    List<String> recipients = getRandomRecipients();

    CreateMessageDTO message = CreateMessageDTO.builder()
      .sender(sender)
      .payload(payload)
      .recipients(recipients)
      .build();

    log.info("Generated message: {}", message);
    jmsTemplate.convertAndSend(JmsConfig.MESSAGE_QUEUE, message);
    log.info("Message sent to MQ.");
  }

  private String generateRandomPayload() {
    String type = getRandom(List.of("PAYMENT", "ALERT", "NOTIFICATION"));
    switch (type) {
      case "PAYMENT":
        int ref = random.nextInt(90000) + 10000;
        double amount = Math.round((50 + random.nextDouble() * 4950) * 100.0) / 100.0;
        String currency = getRandom(List.of("EUR", "USD", "GBP", "JPY"));
        return String.format("PAYMENT|REF:%d|AMOUNT:%.2f|CURRENCY:%s", ref, amount, currency);
      case "ALERT":
        String level = getRandom(List.of("LOW", "MEDIUM", "HIGH", "CRITICAL"));
        return String.format("ALERT|LEVEL:%s|TIMESTAMP:%s", level, LocalDateTime.now());
      case "NOTIFICATION":
        String topic = getRandom(List.of("FX_RATE_UPDATE", "DAILY_BALANCE", "NEW_INSTRUCTION"));
        return String.format("NOTIFICATION|TOPIC:%s|DATE:%s", topic, LocalDate.now());
      default:
        return "UNKNOWN";
    }
  }

  private List<String> getRandomRecipients() {
    int count = random.nextInt(3) + 1; // 1 to 3 recipients
    return recipientsPool.stream()
      .sorted((a, b) -> random.nextInt(3) - 1)
      .limit(count)
      .toList();
  }

  private <T> T getRandom(List<T> list) {
    return list.get(random.nextInt(list.size()));
  }
}
