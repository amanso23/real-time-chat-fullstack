import { Component, OnInit } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { ChatService } from '../../services/services';
import { ChatResponse } from '../../services/models';
import { IconComponent } from '../../components/shared/icon/icon.component';

@Component({
  selector: 'app-main',
  imports: [IconComponent, ChatListComponent],
  templateUrl: './main.component.html',
  styles: '',
})
export class MainComponent implements OnInit {
  readonly iconClass = 'size-6 text-white cursor-pointer';

  chatList: ChatResponse[] = [];

  constructor(private readonly chatService: ChatService) {}

  ngOnInit(): void {
    this.getAllChats();
  }

  private getAllChats() {
    this.chatService.getChatsByReciever().subscribe({
      next: (res) => {
        this.chatList = Array.isArray(res) ? res : res ? [res] : [];
      },
      error: (err) => {
        console.error('Error fetching chat list:', err);
      },
    });
  }
}
