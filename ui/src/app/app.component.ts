import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FileIcon, LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LucideAngularModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'ui';
  readonly FileIcon = FileIcon;
}
