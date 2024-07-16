from abc import ABC
from typing import List

from cache_wrapper.Data import Data


class Cacher(ABC):

    def clear_file_then_persist_all(self, datas: List[Data]):
        raise Exception("Not implemented")

    def clear_file_then_persist(self, data: Data):
        raise Exception("Not implemented")

    def get_line(self) -> Data:
        raise Exception("Not implemented")

    def find_by_key(self, key: str) -> Data:
        raise Exception("Not yet implemented")

    def clean(self):
        raise Exception("Not yet implemented")
