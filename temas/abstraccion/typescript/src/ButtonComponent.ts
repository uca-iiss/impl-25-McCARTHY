import { Component } from './Component';

class ButtonComponent extends Component {
  private label: string;

  constructor(id: string, label: string) {
    super(id);
    this.label = label;
  }

  render(): string {
    return this.isVisible()
      ? `<button id="${this.id}">${this.label}</button>`
      : '';
  }
}

export { ButtonComponent };
