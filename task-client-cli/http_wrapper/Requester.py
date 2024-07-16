import os

import requests

from http_wrapper.Response import Response
from http_wrapper.env_vars import base_url_env_variable
from http_wrapper.exceptions import RequesterNotConfiguredException, ResponseStatusNot200Exception


class Requester:

    def __init__(self, base_url: str = None):
        if base_url is None:
            opt_base_url = os.getenv(base_url_env_variable)
            print(f"env var: \"{opt_base_url}\"")
            if opt_base_url is None or len(opt_base_url) == 0:
                raise RequesterNotConfiguredException()
            base_url = opt_base_url
        self.base_url = base_url

    def get(self, endpoint) -> Response:
        url = self.base_url + endpoint
        response = requests.get(url=url)
        _validate_status_code(response.status_code)
        return Response(
            status_code=response.status_code,
            content=response.json()
        )

    def post(self, endpoint: str, body=None) -> Response:
        url = self.base_url + endpoint
        print("url: " + url)
        response = requests.post(
            url=url,
            json=body
        )
        _validate_status_code(response.status_code)
        return _map_response_content(response)

    def put(self, endpoint: str, json=None) -> Response:
        url = self.base_url + endpoint
        response = requests.put(
            url=url,
            json=json
        )
        _validate_status_code(response.status_code)
        return _map_response_content(response)


def _map_response_content(response):
    content = None
    if len(response.text) != 0:
        content = response.json()
    return Response(
        status_code=response.status_code,
        content=content
    )


def _validate_status_code(status_code):
    if status_code < 200 or status_code > 299:
        raise ResponseStatusNot200Exception()
