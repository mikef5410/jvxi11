/**
 * Copyright - See the COPYRIGHT that is included with this distribution.
 * EPICS pvData is distributed subject to a Software License Agreement found
 * in file LICENSE that is included with this distribution.
 */
package jvxi11;

public enum VXI11ErrorCode {
    noError,
    syntaxError,
    deviceNotAccessable,
    invalidLinkIdentifier,
    parameterError,
    channelNotEstablished,
    operationNotSupported,
    outOfResources,
    deviceLockedByAnotherLink,
    noLockHeldByThisLink,
    IOTimeout,
    IOError,
    invalidAddress,
    abort,
    channelAlreadyEstablished,
    unknownHostException,
    IOException,
    RPCException,
    unknown;
}
