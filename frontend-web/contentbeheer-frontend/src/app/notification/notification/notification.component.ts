import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {NotificationService} from "../../services/notification/notification.service";

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent implements OnInit, OnDestroy {
  notifications: string[] = [];
  notificationSubscription: Subscription | undefined;
  notificationService: NotificationService = inject(NotificationService);

  constructor() {}

  ngOnInit(): void {
    console.log('NotificationComponent initialized');
    console.log('Notifications voor sub:', this.notifications);
    this.notificationSubscription = this.notificationService.notificationSubject.subscribe({
      next: (notifications: string[]) => {
        this.notifications = notifications;
        console.log('Notifications na sub:', this.notifications);
      },
      error: (err) => {
        console.error('Error in receiving notifications:', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.notificationSubscription) {
      this.notificationSubscription.unsubscribe();
    }
  }
}
