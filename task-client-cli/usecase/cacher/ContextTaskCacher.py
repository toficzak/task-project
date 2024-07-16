from cache_wrapper.Cacher import Cacher
from cache_wrapper.FileCacher import FileCacher


class ContextTaskCacher:
    def __init__(self):
        self._name = "context_task"
        self.cacher = FileCacher(self._name)

    # todo: demeter law not happy, but is there any sense to proxy functions? Think later
    def get_cacher(self) -> Cacher:
        return self.cacher
