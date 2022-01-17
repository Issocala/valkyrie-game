package application.module.player.base.data.domain;

/**
 * @author Luo Yong
 * @date 2021-11-30
 * @Source 1.0
 */
public record Person(String name, Integer age) {

    public static class Builder {
        String name;
        Integer age;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(name, age);
        }
    }
}
