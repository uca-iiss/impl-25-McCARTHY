interface Renderable {
  render(): string;
}

abstract class Component implements Renderable {
  protected readonly id: string;
  private visible: boolean = true;

  constructor(id: string) {
    this.id = id;
  }

  show(): void {
    this.visible = true;
  }

  hide(): void {
    this.visible = false;
  }

  isVisible(): boolean {
    return this.visible;
  }

  abstract render(): string;
}

export { Component, Renderable };
