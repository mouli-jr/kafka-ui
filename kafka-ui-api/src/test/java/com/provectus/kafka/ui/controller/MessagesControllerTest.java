package com.provectus.kafka.ui.controller;

import com.provectus.kafka.ui.config.ClustersProperties;
import com.provectus.kafka.ui.model.TopicMessageEventDTO;
import com.provectus.kafka.ui.service.MessagesService;
import com.provectus.kafka.ui.service.rbac.AccessControlService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@SpringBootTest
class MessagesControllerTest {

  private static final String TOPICS_PREFIX = "test-";

  @Mock
  MessagesService messagesService;

  @Mock
  AccessControlService accessControlService;

  @Mock
  ClustersProperties clustersProperties;

  @Captor
  ArgumentCaptor<Integer> limitCaptor;

  @InjectMocks
  @Spy
  MessagesController messagesController;

  @Test
  public void getTopicMessagesDefaultLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, null, null, null, null, null,
        null, null);

    Assert.assertEquals(20,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitLessThanMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(100);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 53, null, null, null, null,
        null, null);

    Assert.assertEquals(53,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitEqualToMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(100);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 100, null, null, null, null,
        null, null);

    Assert.assertEquals(100,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitGreaterThanMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(100);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 200, null, null, null, null,
        null, null);

    Assert.assertEquals(100,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitEqualToZeroTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(100);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 0, null, null, null, null,
        null, null);

    Assert.assertEquals(0,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesDefaultLimitCustomMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(200);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, null, null, null, null, null,
        null, null);

    Assert.assertEquals(20,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitLessThanMaxLimitCustomMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(200);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 53, null, null, null, null,
        null, null);

    Assert.assertEquals(53,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitEqualToMaxLimitCustomMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(200);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 200, null, null, null, null,
        null, null);

    Assert.assertEquals(200,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitGreaterThanMaxLimitCustomMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(200);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 300, null, null, null, null,
        null, null);

    Assert.assertEquals(200,limitCaptor.getValue(),0);

  }

  @Test
  public void getTopicMessagesCustomLimitEqualToZeroCustomMaxLimitTest() {

    String testTopic = TOPICS_PREFIX + UUID.randomUUID();

    Mockito.when(messagesService.loadMessages(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any(), limitCaptor.capture(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(Flux.just(new TopicMessageEventDTO()));

    Mockito.when(accessControlService.validateAccess(Mockito.any()))
        .thenReturn(Mono.empty());

    Mockito.when(clustersProperties.getPageSizeLimit()).thenReturn(200);

    Mockito.doReturn(null).when(messagesController).getCluster(Mockito.any());

    messagesController.getTopicMessages("LOCAL", testTopic, null, null, 0, null, null, null, null,
        null, null);

    Assert.assertEquals(0,limitCaptor.getValue(),0);

  }

}
