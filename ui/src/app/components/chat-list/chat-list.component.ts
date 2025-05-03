import { Component, input, InputSignal } from '@angular/core';
import { ChatResponse, UserResponse } from '../../services/models';
import { IconComponent } from '../shared/icon/icon.component';
import { DatePipe } from '@angular/common';
import { UserService } from '../../services/services/user.service';

@Component({
  selector: 'app-chat-list',
  imports: [DatePipe, IconComponent],
  templateUrl: './chat-list.component.html',
  styles: '',
})
export class ChatListComponent {
  selectContact(arg0: number | undefined) {
    throw new Error('Method not implemented.');
  }
  chatList: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact: boolean = true;
  contacts: UserResponse[] = [];

  constructor(private userService: UserService) {}

  wrapMessage(lastMessageContent: string) {
    return lastMessageContent.length > 20
      ? lastMessageContent.substring(0, 20) + '...'
      : lastMessageContent;
  }
  openChat(arg0: string | undefined) {
    throw new Error('Method not implemented.');
  }
  searchContact() {
    this.userService.findAllUsersExceptSelf().subscribe({
      next: (res) => {
        console.log(res);

        this.contacts = Array.isArray(res) ? res : res ? [res] : [];
        this.searchNewContact = true;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      },
    });
  }
}
