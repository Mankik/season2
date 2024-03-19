// the implementation
export function hello() {
  console.log("Hello, World!!");
}

export function printNum(msg: number) {
  console.log("num: " + msg);
}

export function add(...args: number[]) {
  return args.reduce((a, b) => a + b, 0);
}

// in-source test suites
if (import.meta.vitest) {
  const { it, expect, vi } = import.meta.vitest;

  it("hello", () => {
    console.log = vi.fn();

    hello(); // First
    expect(console.log).toBeCalledWith("Hello, World!!");
    hello(); // Again
    expect(console.log).toBeCalledWith("Hello, World!!");
  });

  it("print", () => {
    console.log = vi.fn();

    printNum(0);
    expect(console.log).toBeCalledWith("num: 0");
    printNum(1);
    expect(console.log).toBeCalledWith("num: 1");
    printNum(5);
    expect(console.log).toBeCalledWith("num: 5");
  });

  it("add", () => {
    expect(add()).toBe(0);
    expect(add(1)).toBe(1);
    expect(add(1, 2, 3)).toBe(6);
  });
}
