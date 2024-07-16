class CommunicationException(Exception):
    """Error while performing request"""

    def __init__(self, original_exception: Exception = None):
        self.original_exception = original_exception


class ResponseStatusNot200Exception(Exception):
    """Response status code is not 200"""


class RequesterNotConfiguredException(Exception):
    """Requester isnot configured properly"""
