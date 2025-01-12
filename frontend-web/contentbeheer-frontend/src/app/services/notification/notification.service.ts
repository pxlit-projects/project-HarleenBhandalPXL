import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {BehaviorSubject, Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  client: Client;
  notifications: string[] = [];
  notificationSubject: BehaviorSubject<string[]> = new BehaviorSubject<string[]>(this.notifications);

  constructor() {
    this.client = new Client({
      brokerURL: undefined,
      connectHeaders: {},
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      webSocketFactory: () => {
        return new SockJS('http://localhost:8083/ws');
      }
    });

    this.client.onConnect = (frame) => {
      this.client.subscribe('/topic/notification', (message: Message) => {
        this.notifications.push(message.body);
        this.notificationSubject.next([...this.notifications]);
        console.log('Received message:', message.body);
        console.log('Notifications on connect in service:', this.notifications);
      });
    };

    this.client.activate();
  }
}
