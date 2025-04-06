import { Component } from "@angular/core";

@Component({
    selector: 'bopr-footer',
    template: `
    <footer class="footer">
      <span>&copy; {{year}} Sébastien BRUNO - All rights reserved</span>
    </footer>
    `,
    styles: [`
    .footer {
      width: 100%;
      text-align: center;
      padding: 12px 0;
      font-size: 0.875rem;
      color: #666;
      background-color: #f4f4f4;
      border-top: 1px solid #ddd;
    }

    `],
})
export class FooterComponent {

    year =  new Date().getFullYear();
}