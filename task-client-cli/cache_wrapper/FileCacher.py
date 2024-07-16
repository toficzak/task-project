import os
from typing import List, Optional

from cache_wrapper.Cacher import Cacher
from cache_wrapper.Data import Data
from cache_wrapper.consts import FILE_SEPARATOR
from cache_wrapper.exceptions import NoSuchFileException, EmptyFileException


class FileCacher(Cacher):

    def __init__(self, cache_name):
        self.cache_dir_path = "./"
        self.cache_name = cache_name
        self.path = f"{self.cache_dir_path}/cache_{self.cache_name}.csv"

    def clear_file_then_persist(self, data: Data):
        self.clear_file_then_persist_all([data])

    def clear_file_then_persist_all(self, datas: List[Data]):

        output = ""

        for data in datas:
            value_part = f"{FILE_SEPARATOR}"
            if data.value is not None:
                value_part += f"{data.value}"
            name_part = f"{FILE_SEPARATOR}"
            if data.name is not None:
                name_part += f"{data.name}"
            row = f"{data.key}{value_part}{name_part}\n"
            output += row

        with open(self.path, "w") as file:
            file.write(output)

    def get_line(self) -> Data:
        self._file_exists_or_raise()
        with open(self.path, "r") as file:
            line = file.readline()
            if line is None:
                print("No context task list set")
                raise EmptyFileException()
            parts = line.split(FILE_SEPARATOR)
            return Data(
                key=parts[0],
                value=parts[1],
                name=parts[2]
            )

    def find_by_key(self, key: str) -> Optional[Data]:
        self._file_exists_or_raise()
        with open(self.path, "r") as file:
            for line in file.readlines():
                parts = line.split(FILE_SEPARATOR)
                if parts[0] == str(key):
                    return Data(parts[0], parts[1], parts[2])
            return None

    def _file_exists_or_raise(self):
        if not os.path.isfile(self.path):
            print(f"No {self.cache_name} file found. Expected {self.path}")
            raise NoSuchFileException()
