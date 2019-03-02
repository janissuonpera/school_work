ajab = [("a", 7), ("b", 6), ("c", 5), ("d", 4), ("e", 1), ("f", 3), ("g", 2)]

ajab.sort(key=lambda a: a[1], reverse=True)

print(ajab)
