export const endpoints = {
  endpointExample: '/example',
} as const;

export type EndpointKey = (typeof endpoints)[keyof typeof endpoints];
