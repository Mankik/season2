export const boolVal = true;
export const nullVal = null;
export let numVal = 10;

export const obj = {
  name: "Hello",
  age: 30,
  items: [
    {
      type: "apple",
      count: 2
    },
    {
      type: "computer",
      count: 4
    }
  ]
};

export const objWithFn = {
  ...obj,
  getName() {
    return this.name;
  },
  setName(name: typeof this.name) {
    this.name = name;
  }
};

// in-source test suites
if (import.meta.vitest) {
  const { it, expect } = import.meta.vitest;
  const { spyOn } = import.meta.vitest.vi;

  it("bool", () => {
    expect(boolVal).toBe(true);
    expect(boolVal).toBeTruthy();
    expect(boolVal).toBeTypeOf("boolean");
  });

  it("null", () => {
    expect(nullVal).toBe(null);
    expect(nullVal).toBeNull();
  });

  it("number", () => {
    expect(numVal).toBe(10);
    expect(numVal).toBeTypeOf("number");

    numVal = 5;
    expect(numVal - 1).toBe(4);
    expect(numVal).toBeGreaterThan(4);
    expect(numVal).toBeGreaterThanOrEqual(5);
    expect(numVal).toBeLessThan(6);
    expect(numVal).toBeLessThanOrEqual(5);
  });

  it("obj", () => {
    expect(obj).toBeTypeOf("object");
    expect(obj).not.toBeNull();
    expect(obj).toHaveProperty("name");
    expect(obj).toHaveProperty("age", 30);
    expect(obj).toHaveProperty("items[0].type", "apple");
    expect(obj).toHaveProperty("items[1].count", 4);
    expect(obj).toEqual({
      name: "Hello",
      age: 30,
      items: [
        {
          type: "apple",
          count: 2
        },
        {
          type: "computer",
          count: 4
        }
      ]
    });
  });

  it("objWithFn", () => {
    expect(objWithFn).toBeTypeOf("object");
    expect(objWithFn).toHaveProperty("getName");
    expect(objWithFn).toHaveProperty("setName");

    spyOn(objWithFn, "getName");
    spyOn(objWithFn, "setName");
    objWithFn.setName("Lee");
    const name = objWithFn.getName();

    expect(objWithFn.setName).toHaveBeenCalledWith("Lee");
    expect(objWithFn.getName).toHaveBeenCalled();
    expect(name).toBe("Lee");
  });
}
