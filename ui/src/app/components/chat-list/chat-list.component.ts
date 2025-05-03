import { Component, input, InputSignal } from '@angular/core';
import { ChatResponse } from '../../services/models';
import { IconComponent } from '../shared/icon/icon.component';

@Component({
  selector: 'app-chat-list',
  imports: [IconComponent],
  templateUrl: './chat-list.component.html',
  styles: '',
})
export class ChatListComponent {
  searchContact() {
    throw new Error('Method not implemented.');
  }
  chatList: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact: boolean = true;
}
