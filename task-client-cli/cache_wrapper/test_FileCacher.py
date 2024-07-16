import os
import unittest

from cache_wrapper.Data import Data
from cache_wrapper.FileCacher import FileCacher
from cache_wrapper.consts import FILE_SEPARATOR

TEST_FILE_PATH = "./cache_test_cache.csv"
KEY = "test_key"
VALUE = "test_value"
NAME = "test_name"


class MyTestCase(unittest.TestCase):
    def test_should_persist_with_name(self):
        cacher = FileCacher("test_cache")
        cacher.clear_file_then_persist_all(Data(KEY, VALUE, NAME))

        with open(TEST_FILE_PATH, "r") as file:
            line = file.readline()
            self.assertIsNotNone(line)
            self.assertEqual(line, f"{KEY}{FILE_SEPARATOR}{VALUE}{FILE_SEPARATOR}{NAME}")

        os.remove(TEST_FILE_PATH)

    def test_should_persist_without_name(self):
        cacher = FileCacher("test_cache")
        cacher.clear_file_then_persist_all(Data(KEY, VALUE))

        with open(TEST_FILE_PATH, "r") as file:
            line = file.readline()
            self.assertIsNotNone(line)
            self.assertEqual(line, f"{KEY}{FILE_SEPARATOR}{VALUE}{FILE_SEPARATOR}")

        os.remove(TEST_FILE_PATH)

    def test_find_key(self):
        cacher = FileCacher("test_cache")
        cacher.clear_file_then_persist_all([
            Data("123", "value1", "name1"),
            Data("234", "value2", "name2"),
            Data("345", "value3", "name3"),
        ])
        result = cacher.find_by_key("234")

        assert result.key == "234"
        assert result.value == "value2"
        assert result.name == "name2"


if __name__ == '__main__':
    unittest.main()
