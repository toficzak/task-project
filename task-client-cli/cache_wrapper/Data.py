class Data:
    def __init__(self, key: str, value: str = None, name: str = None):
        self.key = key.strip()
        if value is not None:
            self.value = value.strip()
        else:
            self.value = None
        if name is not None:
            self.name = name.strip()
        else:
            self.name = None

    def __repr__(self):
        return f"Data(key: {self.key}, name: {self.name}, value: {self.value})"
