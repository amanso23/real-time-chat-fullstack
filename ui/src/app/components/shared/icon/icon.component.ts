import { Component, Input } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { ICONS } from './icons';

@Component({
  selector: 'app-icon',
  imports: [LucideAngularModule],
  templateUrl: './icon.component.html',
  styles: ``,
})
export class IconComponent {
  @Input() icon: keyof typeof ICONS = 'UserIcon';
  @Input() color: string = 'text-white';
  readonly icons = ICONS;

  get iconClass() {
    return `size-6 cursor-pointer ${this.color}`;
  }
}
