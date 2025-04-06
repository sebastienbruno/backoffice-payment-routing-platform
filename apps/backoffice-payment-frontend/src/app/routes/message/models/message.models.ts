export type Message = {
    id: number;
    sender: string;
    recipients: string[];
    payload: string;
}

export type MessageDetailDataDialog = Message;