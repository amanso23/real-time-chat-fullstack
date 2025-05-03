import { Component } from '@angular/core';
import { IconComponent } from '../shared/icon/icon.component';

@Component({
  selector: 'app-greet',
  imports: [IconComponent],
  templateUrl: './greet.component.html',
  styles: ``,
})
export class GreetComponent {
  currentHour = new Date().getHours();
  greeting = '';

  constructor() {
    this.getGreeting();
  }

  getGreeting() {
    if (this.currentHour < 12) {
      this.greeting = 'Good Morning!';
    } else if (this.currentHour < 18) {
      this.greeting = 'Good Afternoon!';
    } else {
      this.greeting = 'Good Evening!';
    }
  }
}
