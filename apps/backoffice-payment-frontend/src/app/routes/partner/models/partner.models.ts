import { components } from "../../../shared/models/partner-api";

export type Partner = components["schemas"]["PartnerDTO"];
export type PartnerDTO = Partner;
export type CreatePartner = components["schemas"]["CreatePartnerDTO"];

export const Direction = {
    INBOUND: 'INBOUND',
    OUTBOUND: 'OUTBOUND',
} as const;
export type Direction = typeof Direction[keyof typeof Direction];
export const DIRECTION_LIST: Array<Direction> = Object.values(Direction);

export const ProcessedFlowType = {
    MESSAGE: "MESSAGE",
    ALERTING: "ALERTING",
    NOTIFICATION: "NOTIFICATION",
} as const;
export type ProcessedFlowType = typeof ProcessedFlowType[keyof typeof ProcessedFlowType];
export const PROCESSED_FLOW_TYPE_LIST: Array<ProcessedFlowType> = Object.values(ProcessedFlowType);
