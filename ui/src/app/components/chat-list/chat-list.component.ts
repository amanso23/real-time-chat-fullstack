import { Component, Input } from '@angular/core';
import { ChatResponse } from '../../services/models';

@Component({
  selector: 'app-chat-list',
  imports: [],
  templateUrl: './chat-list.component.html',
  styles: '',
})
export class ChatListComponent {
  @Input() chatList: ChatResponse[] = [];
}
