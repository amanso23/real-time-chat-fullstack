import { Component, OnInit } from '@angular/core';
import {
  LucideAngularModule,
  MessageCircleIcon,
  UserIcon,
  DoorOpen,
} from 'lucide-angular';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { KeycloakService } from '../../utils/keycloak/keycloak.service';
import { ChatService } from '../../services/services';
import { ChatResponse } from '../../services/models';

@Component({
  selector: 'app-main',
  imports: [LucideAngularModule, ChatListComponent],
  templateUrl: './main.component.html',
  styles: '',
})
export class MainComponent implements OnInit {
  readonly MessageCircleIcon = MessageCircleIcon;
  readonly UserIcon = UserIcon;
  readonly DoorOpen = DoorOpen;

  readonly iconClass = 'size-6 text-white cursor-pointer';

  chatList: ChatResponse[] = [];

  constructor(private readonly chatService: ChatService) {}

  ngOnInit(): void {
    this.getAllChats();
  }

  private getAllChats() {
    this.chatService.getChatsByReciever().subscribe({
      next: (res) => {
        this.chatList = res;
      },
      error: (err) => {
        console.error('Error fetching chat list:', err);
      },
    });
  }
}
