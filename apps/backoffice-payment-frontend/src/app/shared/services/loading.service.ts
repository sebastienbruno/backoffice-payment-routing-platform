import { Injectable, signal } from "@angular/core";

@Injectable({
    providedIn: 'root',
})
export class LoadingService {

    #isLoading = signal(true);
    isLoading = this.#isLoading.asReadonly();

    load() {
        this.#isLoading.set(true);
    }

    loaded() {
        setTimeout(() => this.#isLoading.set(false), 350);
    }

}